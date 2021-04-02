using FluentValidation;
using System;
using System.Collections.Generic;
using System.Text;

namespace Planner.Application.Account.Login
{
    public class LoginValidation: AbstractValidator<LoginCommand>
	{
		public LoginValidation()
		{
			RuleFor(x => x.Email).NotEmpty();

			RuleFor(x => x.Password).NotEmpty();
		}
	}
    
}
