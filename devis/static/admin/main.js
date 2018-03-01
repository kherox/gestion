 
function onChecked(){
    
   productlist  =  $(".addCategories");
   panel        = $("#addProductToCategoriesPanel");
   inputs       =  productlist.find('input');
   value        = prompt("Entrer son ensemble");

//    if (value != null){
//     data.push({"service" : value});
//    }

   

   var content = "";
   for (let index = 0; index < inputs.length; index++) {
       const element = inputs[index];
      
       if (element.checked){
           content += "<tr><td><input type='checkbox' checked='checked' name="+element.name+"_"+value+" id="+element.id+"></td><td>"+ element.name +"</td>";
           content += "<td>"+ value +"</td></tr>";
           element.checked = false;
         
         }
    }
    panel.append(content);

    
    

   

   
}

function csrfSafeMethod(method) {
    // these HTTP methods do not require CSRF protection
    return (/^(GET|HEAD|OPTIONS|TRACE)$/.test(method));
}



// using jQuery
function getCookie(name) {
    var cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        var cookies = document.cookie.split(';');
        for (var i = 0; i < cookies.length; i++) {
            var cookie = jQuery.trim(cookies[i]);
            // Does this cookie string begin with the name we want?
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}


function validate(){
   productlist  =  $(".preinvoice");
   inputs       =  productlist.find('input');
   data         = [];
   projectid    = $("#projectid").val();
   var content = "";
   for (let index = 0; index < inputs.length; index++) {
    const element = inputs[index];
   
    if (element.checked){
        if (element.name == "compact") {
            data.push({compact: true})
        }else if (element.name == "nocompact"){
            data.push({compact: false})
        }else{
            content+= element.name+"_"+element.id+";"; 
        }
      }
   }
   data.push({content   : content});
   data.push({projectid : projectid});

   var csrftoken = getCookie('csrftoken');

   $.ajaxSetup({
       beforeSend: function(xhr, settings) {
           if (!csrfSafeMethod(settings.type) && !this.crossDomain) {
               xhr.setRequestHeader("X-CSRFToken", csrftoken);
           }
       }
   });
   

  $.ajax({
      method    : "POST",
      url       : "/devis/updateinformations", //{% url 'updateinformations' %},
      //url       : "/devis/updateinformations?data="+data,
      data      : {data : JSON.stringify(data) },
      success   : function(data){
        document.location.href = "/admin/devis";

      }
  })
  
}