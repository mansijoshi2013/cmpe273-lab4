

$(":button").click(function() {
        var isbn = this.id;
        alert('About to report lost on ISBN ' + isbn);

    $.ajax({
        url: '/library/v1/books/' + isbn + '/?status=lost',
     	contentType: "application/json",
        type: 'PUT',
        success: function() {
                                alert('Reported lost on ISBN ' + isbn); 
                                window.location.reload(); 
                },
        error: function() {
                        alert("Error! Please try again.");
                }
        
        });
    
});   

$(document).ready(function() {
        var table = document.getElementById("books_table");
        for (var i = 0, row; row = table.rows[i]; i++) {
                        var status=table.rows[i].cells[4].innerHTML;
                        if (status=="lost")
                                {
                                var isbnID=table.rows[i].cells[0].innerHTML;
                                $("#"+isbnID).prop("disabled",true);
                                }
                }
}); 

