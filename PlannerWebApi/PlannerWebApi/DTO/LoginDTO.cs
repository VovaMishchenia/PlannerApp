using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace PlannerWebApi.DTO
{
    public class LoginDTO
    {
        [Required(ErrorMessage ="Required field"),
            EmailAddress(ErrorMessage ="Not valid email")]
        public string Email { get; set; }
        [Required(ErrorMessage ="Required field")
           ]
        public string Password { get; set; }
    }
}
