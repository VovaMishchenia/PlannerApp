using System;
using System.Collections.Generic;
using System.Text;
using MediatR;
namespace Planner.Application.Account.Login
{
    public class LoginCommand: IRequest<UserViewModel>
    {
        public string Email { get; set; }

        public string Password { get; set; }
    }
    
    
}
