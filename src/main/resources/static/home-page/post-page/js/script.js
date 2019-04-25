

window.addEventListener('DOMContentLoaded', function() {
        function getInfo() {
            var xhr = new XMLHttpRequest();
            xhr.open('GET', 'http://localhost:9999/posts/all', true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status >= 200 && status <= 299) {
                        const data = JSON.parse(xhr.responseText);
                        for(var i = 0;i<data.length;i++){
                        const content = `<div class="container">

                                             <div class="row">

                                                 <div class="col-lg-8">

                                                     <h1 class="mt-4">${data[i].title}</h1>

                                                     <!-- Author -->
                                                     <p class="lead">
                                                         by
                                                         <a>${(data[i].user.firstName)+" "+(data[i].user.lastName)}</a>
                                                     </p>

                                                     <hr>

                                                     <p>Active period from ${data[i].startDate} to ${data[i].endDate}</p>

                                                     <hr>
                                                     <img class="img-fluid rounded" src="post-page/images/default.jpg">
                                                     <hr>

                                                     <!-- Post Content -->
                                                     <h4>Description</h4>
                                                     <p class="lead">${data[i].description}</p>

                                                     <hr>
                                                     <div class="card my-4">
                                                         <h5 class="card-header">Leave a Comment:</h5>
                                                         <div class="card-body">
                                                             <form>
                                                                 <div class="form-group">
                                                                     <textarea class="form-control" rows="3"></textarea>
                                                                 </div>
                                                                 <button type="submit" class="btn btn-primary">Submit</button>
                                                             </form>
                                                         </div>
                                                     </div>
                                                     <div class="media mb-4">
                                                         <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
                                                         <div class="media-body">
                                                             <h5 class="mt-0">Commenter Name</h5>
                                                             <p>Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.</p>
                                                         </div>
                                                     </div>
                                                 </div>

                                                 <!-- Sidebar Widgets Column -->
                                                 <div class="col-md-4">
                                                 </div>

                                             </div>
                                             <!-- /.row -->

                                         </div>`;

                        document.getElementById('container').innerHTML += content;
                        }
                    }
                }
            }
            xhr.send();
        }
        if(window.addEventListener) {
            window.addEventListener('load',getInfo,false);
        } else {
            window.attachEvent('onload',getInfo);
        }
    });