using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Planner.Application;
using Planner.Application.Account.Login;
using Planner.Application.Account.Registration;
using Planner.WebApi.Controllers;
using PlannerWebApi.DTO;
using PlannerWebApi.Helpers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace PlannerWebApi.Controllers
{
   [AllowAnonymous]
    public class AccountController : BaseController
    {
        //[HttpPost]
        //[Route("login")]
        //public async Task<IActionResult> Login([FromBody]LoginDTO model) {
        //    if (!ModelState.IsValid)
        //    {
        //        return BadRequest(CustomValidator.GetErrorsByModel(ModelState));

        //    } 
        //    return Ok(new
        //    {
        //        token = "asdadaashgasdyyutuytu"
        //    }); 
        //}

        [HttpPost("login")]
        public async Task<ActionResult<UserViewModel>> LoginAsync(LoginCommand query)
        {
            return await Mediator.Send(query);
        }
        [HttpPost("registration")]
        public async Task<ActionResult<UserViewModel>> RegistrationAsync(RegistrationCommand command)
        {
            return await Mediator.Send(command);
        }
    }
}

