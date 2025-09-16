
$(document).ready(function() {
			$('#upload').on('change', function() {
				var file = this.files[0];
				var formData = new FormData();
				formData.append("multipartFile", file);

				$.ajax({
					url: 'csv',
					type: 'POST',
					data: formData,
					processData: false,
					contentType: false,
					success: function(data) {
                        setInterval('refreshPage()',200);
					},
					error: function(jqXHR, textStatus, errorThrown) {
						console.error('Error requesting file: ' + textStatus);
					}
				});
			});
		});

function refreshPage() {
    location.reload();
}

function goToUrl() {
  window.location = 'csv-download';
}

//$(document).ready(function() {
//  // Check if the table has any rows
//  if ($('#data tbody tr').length > 0) {
//    // If the table has at least one row, show the table
//    $('#data').show();
//    $('#pageable').show();
//  } else {
//    // If the table has no rows, hide the table
//    $('#data').hide();
//    $('#pageable').hide();
//  }
//});

function sendDelete(url) {
        var xhttp = new XMLHttpRequest();
        xhttp.open("DELETE", url, true);
        xhttp.onload = function () {
            let responseURL = xhttp.responseURL;
            console.log("Redirecting to:", responseURL);
            window.location.replace(responseURL);
        };
        xhttp.send();
    }

$(document).ready(function() {
  var dateTimeElement = document.getElementById("dateTime");

  if (dateTimeElement) {
    var timestampString = dateTimeElement.innerHTML;
    var year = timestampString.substr(0, 4);
    var month = timestampString.substr(4, 2);
    var day = timestampString.substr(6, 2);
    var hour = timestampString.substr(8, 2);
    var minute = timestampString.substr(10, 2);

    var convertedTimestamp = year + "-" + month + "-" + day + "T" + hour + ":" + minute;

    document.getElementById("convertedTimestamp").value = convertedTimestamp;
    console.log("time", convertedTimestamp);
  }
});


 function validateForm(event) {

     // check catalog
     var catalogInputs = document.querySelectorAll('input[name="catalog"]');
     var selectedCatalog = false;

     catalogInputs.forEach(function(input) {
         if (input.checked) {
             selectedCatalog = true;
         }
     });

     // check condition user
     var conditionInputs = document.querySelectorAll('input[name="condition"]');
     var selectedCondition = false;

     conditionInputs.forEach(function(input) {
         if (input.checked) {
             selectedCondition = true;
         }
     });
     // check input date
     var titleInput = document.getElementById("titleInput").value.trim();
     var contentInput = document.getElementById("contentInput").value.trim();
     var dateInput = document.getElementById("dateInput").value;
     var selectedDate = false;
     var selectedContent = false;
     var selectedTitle = false;
     if (dateInput != "") {
         selectedDate = true;
     }
     if (contentInput != "") {
         selectedContent = true;
     }
     if (titleInput != "") {
         selectedTitle = true;
     }
     if (!selectedDate) {
         alert("Please select a date.");
         event.preventDefault(); // Prevent form submission
     }
     if (!selectedContent) {
         alert("Please enter content.");
         event.preventDefault(); // Prevent form submission
     }
     if (!selectedTitle) {
         alert("Please enter a title.");
         event.preventDefault(); // Prevent form submission
     }

     if (!selectedCatalog) {
         alert("Please select a catalog option.");
         event.preventDefault(); // Prevent form submission
     }

     if (!selectedCondition) {
         alert("Please select a condition option.");
         event.preventDefault(); // Prevent form submission
     } else {

         // check input file
         var inputs = document.getElementsByName('condition');
         for (var i = 0; i < inputs.length; i++) {
             // Check if the value is equal to 2
             if (inputs[i].value === '2' && inputs[i].checked) {
                 var fileInput = document.getElementById('myFile');
                 var csvInput = document.getElementById("countUser").textContent;
                 if(csvInput=== "XX items"){
                	 var errorFileInput = document.getElementById('errorFileInput');
                     var text = 'CSV file format is incorrect.';
                     errorFileInput.textContent = text;
                     errorFileInput.style.display = "block";
                     event.preventDefault();
                 }
                 if (fileInput.files.length === 0) {
                     var errorFileInput = document.getElementById('errorFileInput');
                     var text = 'Import CSV has not been uploaded.';
                     errorFileInput.textContent = text;
                     errorFileInput.style.display = "block";
                     event.preventDefault();
                 }
//                 else{
//                 if (fileInput.files && fileInput.files[0]) {
//                              const file = fileInput.files[0];
//                              const reader = new FileReader();
//
//                              reader.onload = function(event) {
//                                  const csvData = event.target.result;
//                                  processCSV(csvData);
//                              };
//                              reader.readAsText(file);
//                          }
//                 }
             }
         }
     }
 }

$(document).ready(function() {
    $('#inputCSV').hide();
    $('input[name="condition"]').change(function() {
        if ($(this).val() === '2') {
            $('#inputCSV').show();
        }

        if ($(this).val() === '1' || $(this).val() === '0') {
            $('#inputCSV').hide();
        }
    });
});

     $(document).ready(function() {
       $('#myFile').change(function() {
         var fileName = $(this).val().split('\\').pop();
         if (fileName) {
           $('input[type="button"]').val(fileName);
           $('#errorFileInput').hide();
         } else {
           $('input[type="button"]').val('Upload');
         }
       });
     });

     function handleFileSelect(event) {
    	    const file = event.target.files[0];
    	    const reader = new FileReader();

    	    file.text().then(content => {
    	        
    	        const lines = content.split(/\r\n|\n/); // Split lines by line breaks

    	        // Check if the last line is empty
    	        const lastLineEmpty = lines[lines.length - 1].trim() === '';

    	        if (lines.some(line => line.trim() === '')) {
    	            var errorFileInput = document.getElementById('errorFileInput');
    	            var text = 'CSV file format is incorrect.';
    	            document.getElementById("countUser").textContent = "XX items";
    	            errorFileInput.textContent = text;
    	            errorFileInput.style.display = "block";
    	            return;
    	        }

    	        // Ensure all lines except the last one end with ','
    	        let invalidFormat = false;
    	        const formattedLines = lines.map((line, index) => {
    	            
    	            if (line.trim().length == 32 && line.length == 40) {
    	                invalidFormat = false;
    	            } else {
    	                var dataArray = line.split(',');
    	                var firstCell = dataArray[0];
    	                if (firstCell.length != 40 && firstCell.length != 42) {
    	                    invalidFormat = true;
    	                }
    	            }
    	            return line;
    	        });

    	        if (invalidFormat) {
    	            var errorFileInput = document.getElementById('errorFileInput');
    	            var text = 'CSV file format is incorrect.';
    	            document.getElementById("countUser").textContent = "XX items";
    	            errorFileInput.textContent = text;
    	            errorFileInput.style.display = "block";
    	            return;
    	        } else {
    	            var errorFileInput = document.getElementById('errorFileInput');
    	            errorFileInput.style.display = "none";
    	        }

    	        // Remove empty lines, except the last one if it's empty
    	        const nonEmptyLines = formattedLines.filter((line, index) => {
    	            return index === lines.length - 1 ? !lastLineEmpty : line.trim() !== '';
    	        });

    	        const lineCount = nonEmptyLines.length;

    	        // Display the result
    	        const resultElement = document.getElementById('countUser');
    	                        resultElement.textContent = `${lineCount} items`;
    	    }).catch(error => {
    	        var errorFileInput = document.getElementById('errorFileInput');
    	        var text = 'CSV file format is incorrect.';
    	        errorFileInput.textContent = text;
    	        errorFileInput.style.display = "block";
    	        console.error(error);
    	    });
    	}


     function validateInputPassword(event) {
    	 	event.preventDefault();
    	    var inputElement = document.getElementById('inputPass');
    	    var inputValue = inputElement.value;

    	    // Define a regular expression to enforce the pattern
    	    var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d@!#$%^&*]{8,16}$/;

    	    // Define a regular expression to check for Japanese characters (Hiragana and Kanji)
    	    var kanjiHiraganaRegex = /[\p{Script=Hiragana}\p{Script=Han}]/gu;
    	    
    	    var inputPostCode = document.getElementById('postcode').value;
    	    
    	    if(inputPostCode.length ==0){
    	    	alert("Please enter postal code.");
    	    }else{
    	    	document.getElementById("putForm").submit();
    	    }
//    	    fetch(`https://zipcloud.ibsnet.co.jp/api/search?zipcode=` + inputPostCode)
//            .then(response => {
//                if (!response.ok) {
//                    throw new Error('Network response was not ok');
//                }
//                return response.json();
//            })
//            .then(data => {
//                if (data.results === null) {
//                	alert("The entered postal code does not exist.");
//                } else {
//                	
//                    document.getElementById("putForm").submit();
//                }
//            })
//            .catch(error => {
//                console.error('Error during fetch:', error);
//            });
    	}

function validateFormTemplate() {
            var inputField1 = document.getElementById("title");
            var inputValue1 = inputField1.value.trim();

            var inputField2 = document.getElementById("content");
            var inputValue2 = inputField2.value.trim();

            if (inputValue1 === "" || inputValue2 === "" ) {
                alert("Input cannot be blank.");
                return false; // Prevent form submission
            }

            // If the input is not blank, you can proceed with form submission
            return true;
        }
      document.addEventListener("DOMContentLoaded", function() {
        // Check if the modal element with ID "reloadModal" exists
        const reloadModalElement = document.getElementById("reloadModal");

        if (reloadModalElement) {
          // If the modal element exists, create a Bootstrap modal and show it
          const reloadModal = new bootstrap.Modal(reloadModalElement);
          reloadModal.show();
        }
      });

      function validateFormTemporaryUser(event) {


           var dateFrom = document.getElementById("dateFrom").value;
           var dateTo = document.getElementById("dateTo").value;
           const textFrom = document.getElementById("textFrom");
           const textTo = document.getElementById("textTo");
           var selectedDateFrom = false;
           var selectedDateTo = false;
           if (dateFrom != "") {
               selectedDateFrom = true;
           }
           if (dateTo != "") {
               selectedDateTo = true;
           }

           if (!selectedDateFrom) {
               textFrom.textContent="Please set a date.";
               event.preventDefault(); // Prevent form submission
           }else{
           textFrom.textContent="";
           }

           if (!selectedDateTo) {
               textTo.textContent="Please set a date.";
               event.preventDefault(); // Prevent form submission
           }else{
           textTo.textContent="";
           }

       }

document.addEventListener("DOMContentLoaded", function() {
        var input = document.getElementById("ipatId1");
        var input2 = document.getElementById("ipatId2");
        if (input) {
            input.value = input.value.slice(0, 4);
        }
        if (input2) {
            input2.value = input2.value.slice(0, 4);
        }
    });

    function disableButtonNotification(btn) {
      btn.disabled = true;
      document.getElementById('sendNotification').submit();
    }

    function disableButtonTemplate(btn) {
      btn.disabled = true;
      document.getElementById('createNotification').submit();
    }

    function goBack() {
          window.history.back();
        }

        function disableRightRadio() {
            var rightRadio = document.getElementById("flexRadioDefault2");
                if (rightRadio.checked) {
                    rightRadio.checked = false;
                }
        }

        function disableLeftRadio() {
            var leftRadio = document.getElementById("flexRadioDefault1");
                if (leftRadio.checked) {
                    leftRadio.checked = false;
                }
        }
        
        
        function toggleRadio(radioId) {
            const radio = document.getElementById(radioId);
            if (radio.checked) {
                radio.checked = false;
            }
        }
        
        window.addEventListener('scroll', function() {
        	var element = document.getElementById("scrollHideElement");
        	var scrollPosition = window.scrollY;
            if (scrollPosition > 390) {
                element.classList.remove('show');
            }
        });

        var button = document.getElementById("toggleButton");
        button.addEventListener("click", function() {
            var elementToShow = document.getElementById("scrollHideElement");
            elementToShow.classList.toggle('show');
        });
        
        function limitInputLength() {
        	const inputId = event.target.id;
        	const inputValue = document.getElementById(inputId).value;
        	let fullWidthChars = 0;
        	let halfWidthChars = 0;

        	for (let i = 0; i < inputValue.length; i++) {
        	  const charCode = inputValue.charCodeAt(i);

        	  // Check if the character is full-width or half-width
        	  if ((charCode >= 0x0000 && charCode <= 0x001f) || // Common control characters
        		        (charCode >= 0x0020 && charCode <= 0x007f) || // Common 1-byte ASCII characters
        		        (charCode >= 0xff61 && charCode <= 0xff9f) // SJIS one-byte katakana and symbol range
        		    ) {
        		      // If the character corresponds to a 1-byte character
        		      console.log(`${inputValue[i]} charCode: 0x${charCode.toString(16)} => halfWidth`);
        		      halfWidthChars++;
        		    } else {
        		      // if it does not correspond to a 1-byte character
        		      console.log(`${inputValue[i]} charCode: 0x${charCode.toString(16)} => fullWidth`);
        		      fullWidthChars++;
        		    }

        	}

      	  const totalChars = fullWidthChars * 2 + halfWidthChars;
      	  
      	if (inputId === 'titleUrgent') {
      		if (totalChars>100) {
      			if(100-fullWidthChars <50){
      				const trimmedValue = inputValue.substring(0, Math.min(50, totalChars));
                	  document.getElementById('titleUrgent').value = trimmedValue;
      			}else{
      				const trimmedValue = inputValue.substring(0, Math.min(100-fullWidthChars, totalChars));
                	  document.getElementById('titleUrgent').value = trimmedValue;
      			}
          	  }
      	  }
      	
      	if (inputId === 'contentUrgent') {
      		if (totalChars>4000) {
      			if(4000-fullWidthChars <2000){
      				const trimmedValue = inputValue.substring(0, Math.min(2000, totalChars));
                	  document.getElementById('contentUrgent').value = trimmedValue;
      			}else{
      				const trimmedValue = inputValue.substring(0, Math.min(4000-fullWidthChars, totalChars));
                	  document.getElementById('contentUrgent').value = trimmedValue;
      			}
          	  }
      	  }
      	  
      	  if (inputId === 'raceName') {
      		if (totalChars>20) {
      			if(20-fullWidthChars <10){
      				const trimmedValue = inputValue.substring(0, Math.min(10, totalChars));
                	  document.getElementById('raceName').value = trimmedValue;
      			}else{
      				const trimmedValue = inputValue.substring(0, Math.min(20-fullWidthChars, totalChars));
                	  document.getElementById('raceName').value = trimmedValue;
      			}
          	  }
      	  }
      	if (inputId === 'key') {
      		if (totalChars>4) {
      			if(4-fullWidthChars <2){
      				const trimmedValue = inputValue.substring(0, Math.min(2, totalChars));
                	  document.getElementById('key').value = trimmedValue;
      			}else{
      				const trimmedValue = inputValue.substring(0, Math.min(4-fullWidthChars, totalChars));
                	  document.getElementById('key').value = trimmedValue;
      			}
          	  }
      	  }
      	if (inputId === 'name') {
      		if (totalChars>30) {
      			if(30-fullWidthChars <15){
      				const trimmedValue = inputValue.substring(0, Math.min(15, totalChars));
                	  document.getElementById('name').value = trimmedValue;
      			}else{
      				const trimmedValue = inputValue.substring(0, Math.min(30-fullWidthChars, totalChars));
                	  document.getElementById('name').value = trimmedValue;
      			}
          	  }
      	  }
      	if (inputId === 'value') {
      		if (totalChars>500) {
      			if(500-fullWidthChars <250){
      				const trimmedValue = inputValue.substring(0, Math.min(250, totalChars));
                	  document.getElementById('value').value = trimmedValue;
      			}else{
      				const trimmedValue = inputValue.substring(0, Math.min(500-fullWidthChars, totalChars));
                	  document.getElementById('value').value = trimmedValue;
      			}
          	  }
      	  }
      	if (inputId === 'memo') {
      		if (totalChars>200) {
      			if(200-fullWidthChars <100){
      				const trimmedValue = inputValue.substring(0, Math.min(100, totalChars));
                	  document.getElementById('memo').value = trimmedValue;
      			}else{
      				const trimmedValue = inputValue.substring(0, Math.min(200-fullWidthChars, totalChars));
                	  document.getElementById('memo').value = trimmedValue;
      			}
          	  }
      	  }
      	  
      	if (inputId === 'titleInput') {
      		if (totalChars>40) {
      			if(40-fullWidthChars <20){
      				const trimmedValue = inputValue.substring(0, Math.min(20, totalChars));
                	  document.getElementById('titleInput').value = trimmedValue;
      			}else{
      				const trimmedValue = inputValue.substring(0, Math.min(40-fullWidthChars, totalChars));
                	  document.getElementById('titleInput').value = trimmedValue;
      			}
          	  }
      	  } else if (inputId === 'contentInput') {
      		if (totalChars > 160) {
          	  
          	if(160-fullWidthChars <80){
  				const trimmedValue = inputValue.substring(0, Math.min(80, totalChars));
            	  document.getElementById('contentInput').value = trimmedValue;
  			}else{
  				const trimmedValue = inputValue.substring(0, Math.min(160-fullWidthChars, totalChars));
            	  document.getElementById('contentInput').value = trimmedValue;
  			}
          	  }
      	  } else if (inputId === 'urlInput') {
        		if (totalChars > 2000) {
        			if(2000-fullWidthChars <1000){
          				const trimmedValue = inputValue.substring(0, Math.min(1000, totalChars));
                    	  document.getElementById('urlInput').value = trimmedValue;
          			}else{
          				const trimmedValue = inputValue.substring(0, Math.min(2000-fullWidthChars, totalChars));
                    	  document.getElementById('urlInput').value = trimmedValue;
          			}
              	  }
          	  }
      	if (inputId === 'content') {
      		if (totalChars > 160) {
      			if(160-fullWidthChars <80){
      				const trimmedValue = inputValue.substring(0, Math.min(80, totalChars));
                	  document.getElementById('content').value = trimmedValue;
      			}else{
      				const trimmedValue = inputValue.substring(0, Math.min(160-fullWidthChars, totalChars));
                	  document.getElementById('content').value = trimmedValue;
      			}
          	  }
      	  } else if (inputId === 'url') {
      		if (totalChars > 2000) {
      			if(2000-fullWidthChars <1000){
      				const trimmedValue = inputValue.substring(0, Math.min(1000, totalChars));
                	  document.getElementById('url').value = trimmedValue;
      			}else{
      				const trimmedValue = inputValue.substring(0, Math.min(2000-fullWidthChars, totalChars));
                	  document.getElementById('url').value = trimmedValue;
      			}
          	  }
      	  } else if (inputId === 'title') {
        		if (totalChars > 40) {
        			if(40-fullWidthChars <20){
          				const trimmedValue = inputValue.substring(0, Math.min(20, totalChars));
                    	  document.getElementById('title').value = trimmedValue;
          			}else{
          				const trimmedValue = inputValue.substring(0, Math.min(40-fullWidthChars, totalChars));
                    	  document.getElementById('title').value = trimmedValue;
          			}
              	  }
          	  }
      	else if (inputId === 'email') {
    		if (totalChars > 400) {
    			if(400-fullWidthChars <200){
      				const trimmedValue = inputValue.substring(0, Math.min(200, totalChars));
                	  document.getElementById('email').value = trimmedValue;
      			}else{
      				const trimmedValue = inputValue.substring(0, Math.min(400-fullWidthChars, totalChars));
                	  document.getElementById('email').value = trimmedValue;
      			}
          	  }
      	  }
      	else if (inputId === 'inputPass') {
    		if (totalChars > 32) {
    			if(32-fullWidthChars <16){
      				const trimmedValue = inputValue.substring(0, Math.min(16, totalChars));
                	  document.getElementById('inputPass').value = trimmedValue;
      			}else{
      				const trimmedValue = inputValue.substring(0, Math.min(32-fullWidthChars, totalChars));
                	  document.getElementById('inputPass').value = trimmedValue;
      			}
          	  }
      	  }
      	}

        function removeSpaces(inputField) {
            inputField.value = inputField.value.replace(/\s/g, '');
        }
        
        document.getElementById('resetButton').addEventListener('click', function() {
            document.getElementById('title').value = '';
            document.getElementById('content').value = '';
            document.getElementById('dateInput').value = '';
            document.getElementById('dateInputEnd').value = '';
            document.querySelector('.form-check-input').checked = false;
          });
        
        function validateFormA09(event) {
            event.preventDefault(); // Prevent the default form submission behavior

            var inputField1 = document.getElementById("custom-input-date");
            const inputValue = inputField1.value;
            const formatInput = inputValue.replace(/-/g, '');

            var inputValue1 = inputField1.value.trim();

            var inputField2 = document.getElementById("startDate");
            var inputValue2 = inputField2.value.trim();

            var inputField3 = document.getElementById("endDate");
            var inputValue3 = inputField3.value.trim();

            var inputField4 = document.getElementById("raceName");
            var inputValue4 = inputField4.value.trim();
            if (inputValue1 === "" || inputValue3 === "" || inputValue2 === "" ) {
                alert("Please select a date.");
            } 
            if (inputValue4 === "" ) {
                alert("Please enter race name.");
            }
            if(inputValue1 != "" && inputValue3 != "" && inputValue2 != "" && inputValue4 != ""){
            	fetch(`/WEB/api/checkA09/` + formatInput +`/`+ inputValue4)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    if (!data) {
                        alert('This record already exists.');
                    } else {
                    	
                        document.getElementById("createFeatured").submit();
                    }
                })
                .catch(error => {
                    console.error('Error during fetch:', error);
                });
            }
        }
        
        function validateFormA09Edit(event) {
            event.preventDefault(); // Prevent the default form submission behavior

            var inputField1 = document.getElementById("custom-input-date");
            const inputValue = inputField1.value;
            const formatInput = inputValue.replace(/-/g, '');
            
            var inputIdEdit = document.getElementById("idEdit");
            const inputValueIdEdit = inputIdEdit.value;
            const formatInputIdEdit = inputValueIdEdit.replace(/-/g, '');

            var inputValue1 = inputField1.value.trim();

            var inputField2 = document.getElementById("startDate");
            var inputValue2 = inputField2.value.trim();

            var inputField3 = document.getElementById("endDate");
            var inputValue3 = inputField3.value.trim();

            var inputField4 = document.getElementById("raceName");
            var inputValue4 = inputField4.value.trim();
            
            var inputField5 = document.getElementById("nameOriginal");
            var inputValue5 = inputField5.value.trim();
            if (inputValue1 === "" || inputValue3 === "" || inputValue2 === "" ) {
                alert("Please select a date.");
            } 
            if (inputValue4 === "" ) {
                alert("Please enter race name.");
            }
            if(inputValue1 != "" && inputValue3 != "" && inputValue2 != "" && inputValue4 != ""){
            	fetch(`/WEB/api/checkA09/` + formatInput +`/`+ inputValue4+`/`+formatInputIdEdit+`/`+inputValue5)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    if (!data) {
                        alert('This record already exists.');
                    } else {
                    	
                        document.getElementById("editFeatured").submit();
                    }
                })
                .catch(error => {
                    console.error('Error during fetch:', error);
                });
            }
        }

        function clearInputs() {
            document.getElementById('titleUrgent').value = '';
            document.getElementById('contentUrgent').value = '';
            document.getElementById('dateInput').value = '';
            document.getElementById('dateInputEnd').value = '';
            document.getElementById('inputRadio').checked = false;
            
        }

        function checkForBlankAdmin(event) {
        	event.preventDefault();
            var inputValue = document.getElementById("username").value;
            var encodedInputValue = encodeURIComponent(inputValue);
            fetch(`/WEB/api/checkUserAdmin/` + encodedInputValue)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                if (!data) {
                	window.location.href = `/WEB/admin/login?error`;
                } else {
                	
                    document.getElementById("login").submit();
                }
            })
            .catch(error => {
                console.error('Error during fetch:', error);
            });
        }

        function checkForBlankNotice(event) {
        	event.preventDefault();
            var inputValue = document.getElementById("username").value;
            var encodedInputValue = encodeURIComponent(inputValue);
            fetch(`/WEB/api/checkUserNotice/` + encodedInputValue)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                if (!data) {
                	window.location.href = `/WEB/notification/login?error`;
                } else {
                	
                    document.getElementById("login").submit();
                }
            })
            .catch(error => {
                console.error('Error during fetch:', error);
            });
        }
        
        function validateFormUrgentNotice() {
            var inputField1 = document.getElementById("dateInput");
            var inputValue1 = inputField1.value.trim();

            var inputField2 = document.getElementById("dateInputEnd");
            var inputValue2 = inputField2.value.trim();

            if (inputValue1 === "" || inputValue2 === "" ) {
                alert("Please select a date");
                return false; // Prevent form submission
            }

            return true;
        }