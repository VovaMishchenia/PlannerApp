using MediatR;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Planner.Application.Exceptions;
using Planner.Application.Interfaces;
using Planner.Domain;
using Planner.EFData;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Planner.Application.Account.Registration
{
    public class RegistrationHandler : IRequestHandler<RegistrationCommand, UserViewModel>
    {
		//зміна для управлінням юзерами
        private readonly UserManager<AppUser> _userManager;
		//зміна для генерації JwtToken
        private readonly IJwtGenerator _jwtGenerator;
		//зміна для роботи з контекстом
        private readonly DataContext _context;
		//реалізація Dependency injection
        public RegistrationHandler(DataContext context, UserManager<AppUser> userManager, IJwtGenerator jwtGenerator)
        {
            _context = context;
            _userManager = userManager;
            _jwtGenerator = jwtGenerator;
        }

		//імплементація методу Handle
        public async Task<UserViewModel> Handle(RegistrationCommand request, CancellationToken cancellationToken)
        {
			//перевірка унікальності email
            if (await _context.Users.Where(x => x.Email == request.Email).AnyAsync())
            {
				// Якщо емейл вже зареєстрований в базі, то викидаємо помилку 
                throw new RestException(HttpStatusCode.BadRequest, new { Email = "Email already exist" });
            }
			//перевірка унікальності userName
            if (await _context.Users.Where(x => x.UserName == request.UserName).AnyAsync())
            {
				// Якщо userName вже зареєстрований в базі, то викидаємо помилку 
                throw new RestException(HttpStatusCode.BadRequest, new { UserName = "UserName already exist" });
            }
			//Створюємо юзер для подальшої добавки в базу
            var user = new AppUser
            {
                DisplayName = request.DisplayName,
                Email = request.Email,
                UserName = request.UserName
            };

			//Добавляємо юзера та кешуємо результат
            var result = await _userManager.CreateAsync(user, request.Password);
           
			//Якщо результат успішний, то повертаємо DTO об'єкт
            if (result.Succeeded)
            {
                return new UserViewModel
                {
                    DisplayName = user.DisplayName,
                    Token = _jwtGenerator.CreateToken(user),
                    UserName = user.UserName,
                    Image = null
                };
            }

			//Якщо результат не успішний, то викидаємо помилку
            throw new Exception("Client creation failed");
        }
    }

}
