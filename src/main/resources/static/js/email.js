function validateForm(event) {
        var emailInput = document.getElementById("emailInput");
        var messInput = document.getElementById("messInput");
        var userIdSelect = document.getElementById("userIdSelect");
        var userIdSelect2 = document.getElementById("userIdSelect2");
        var osSelect = document.getElementById("osSelect");
        var emailError = document.getElementById("emailError");
        var userIdError = document.getElementById("userIdError");
        var userId2Error = document.getElementById("userId2Error");
        var osError = document.getElementById("osError");
        var isValid = true;

        emailError.textContent = "";
        messError.textContent = "";
        userIdError.textContent = "";
        userId2Error.textContent = "";
        osError.textContent = "";
        
        if (emailInput.value.trim() === "") {
            emailError.textContent = "Please enter an email address.";
            isValid = false;
            event.preventDefault();
        }else if (!isValidEmailBlank(emailInput.value)) {
                emailError.textContent = "Email address contains whitespace characters.";
                isValid = false;
                event.preventDefault();
            }else if (!isValidEmailFullwidth(emailInput.value)) {
                emailError.textContent = "Email address contains full-width characters.";
                isValid = false;
                event.preventDefault();
            }else if (!isValidEmailValidate(emailInput.value)) {
                emailError.textContent = "Email address format is invalid.";
                isValid = false;
                event.preventDefault();
                
            }else{
            	emailError.textContent = "";
            }
        
        if (messInput.value.trim() === "") {
            messError.textContent = "Please enter inquiry content.";
            isValid = false;
            event.preventDefault();
        }else{
        	messError.textContent = "";
        }

        if (userIdSelect.value.trim() === "") {
            userIdError.textContent = "Please select a category.";
            isValid = false;
            event.preventDefault();
        }else{
        	userIdError.textContent = "";
        }

        if (userIdSelect2.value.trim() === "") {
            userId2Error.textContent = "Please select your device.";
            isValid = false;
            event.preventDefault();
        }
        else{
        	userId2Error.textContent = "";
        }

        if (osSelect.value.trim() === "") {
            osError.textContent = "Please select your OS.";
            isValid = false;
            event.preventDefault();
        }else{
        	osError.textContent = "";
        }

        if(isValid){
        	document.getElementById('sendInquiry').submit();
        	document.getElementById('submitSendInquiry').disabled = true;
        }
    }

function isValidEmailValidate(email) {
	  // Regular expression for email validation
	  var emailPattern = /^\s*[A-Z0-9a-z-!#$%&'*+/=?^_`{|}~]+(?:\.[A-Z0-9a-z-!#$%&'*+/=?^_`{|}~]+)*@[A-Za-z0-9-]+(?:\.[A-Za-z0-9-]+)*\.[A-Za-z]{2,}\s*$/;
	  return emailPattern.test(email);
	}

function isValidEmailBlank(email) {
	  // Regular expression for checking both full-width and half-width blank spaces
	  var spacePattern = /[\s　]/;
	  return !spacePattern.test(email); // Returns true if no spaces are found
	}
function isValidEmailFullwidth(email) {
	  // Regular expression for checking full-width characters
	var fullWidthPattern = /[^\x00-\x7F]/; // Matches any character outside the ASCII range
	  return !fullWidthPattern.test(email); // Returns true if no full-width characters are found
	}
