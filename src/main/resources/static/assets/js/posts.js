function val(index){
    console.log(index);
}
function getInfo(url) {
document.getElementById("container").innerHTML = null;
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status >= 200 && status <= 299) {
                const data = JSON.parse(xhr.responseText);
                for(var i = 0;i<data.length;i++){
                    var imageSrc;
                    if(data[i].imageUrl == null){
                        imageSrc = "assets/images/background4.jpg";
                    }
                    else{
                        imageSrc = "http://localhost:9999/images/posts/"+data[i].imageUrl;
                    }
                    const cid = "comment"+i;
                    const content = `<div class="container">
                                     <div class="row">
                                         <div class="col-lg-8">
                                             <h1 class="mt-4">${data[i].title}</h1>
                                             <!-- Author -->
                                             <p class="lead">
                                                 by
                                                 <p>${(data[i].user.firstName)+" "+(data[i].user.lastName)}</p>
                                             </p>
                                             <hr>
                                             <p>Active period from ${data[i].startDate} to ${data[i].endDate}</p>
                                             <h3 class="mt-4">Donates: ${data[i].donates} $</h3>
                                             <div class="container">
                                                <div class="row justify-content-center">
                                                </div>
                                                <div class="row py-2 justify-content-center">
                                                    <div class="col-12 col-lg-6  col-md-8 " data-form-type="formoid">

                                                        <form action="donate.html" method="GET" class="mbr-form form-with-styler">
                                                            <div class="dragArea row">
                                                                <div class="form-group col" data-for="donate">
                                                                    <input type="number" min="0" step="0.01" name="donate1" placeholder="Amount" data-form-field="Donate" required="required" class="form-control display-7" id="donate1">
                                                                    <input value=${data[i].id} id="postId" name="postId" type="hidden">
                                                                </div>
                                                                <div class="col-auto input-group-btn"><button type="submit" class="btn btn-primary display-4">Donate form</button></div>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                             </div>
                                             <hr>
                                             <img class="img-fluid rounded" src=${imageSrc}>
                                             <hr>
                                             <!-- Post Content -->
                                             <h4>Description</h4>
                                             <p class="lead">${data[i].description}</p>
                                             <hr>
                                             <div class="card my-4">
                                                 <h5 class="card-header">Leave a Comment:</h5>
                                                 <div class="card-body">
                                                     <form action=${"/comments/"+data[i].id} method="post">
                                                         <div class="form-group">
                                                             <textarea name="comment" class="form-control" style="resize:none" rows="3"></textarea>
                                                             <button type="submit" class="btn btn-primary">Submit</button>
                                                         </div>
                                                     </form>
                                                 </div>
                                             </div>
                                             <div id=${cid}></div>
                                         </div>
                                     </div>
                                 </div>`;
                    document.getElementById('container').innerHTML += content;
                    getComments(cid,data[i].id);
                }
            }
        }
    }
    xhr.send();
}

function deleteComment(commentId){
    const delComment = confirm("Are you sure you want to delete that comment?");
    if(delPost === false){
        return;
    }
    const xhr = new XMLHttpRequest();
    xhr.open("DELETE", "http://localhost:9999/comments/"+commentId, true);
    xhr.send(null);
    location.reload();
}

function getComments(cid,postId) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:9999/comments/'+postId, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status >= 200 && status <= 299) {
                const data = JSON.parse(xhr.responseText);
                for(var i = 0;i<data.length;i++){
                var imageSrc;
                if(data[i].user.imageUrl == null){
                    imageSrc  = "/assets/images/icon-122x122.png";
                }
                else{
                    imageSrc  = "http://localhost:9999/images/users/"+data[i].user.id;
                }
                    const content = `<div class="media mb-4">
                                         <img class="d-flex mr-3 rounded-circle" height="42" width="42" src=${imageSrc} alt="">
                                         <div class="media-body">
                                             <h5 class="mt-0">${data[i].user.firstName + " " + data[i].user.lastName}</h5>
                                             <p>${data[i].comment}</p>
                                             <button class="btn btn-secondary btn-form display-4" onclick="deleteComment(${data[i].id});">x</button>
                                         </div>
                                     </div>`;
                    document.getElementById(cid).innerHTML += content;
                }
            }
        }
    }
    xhr.send();
}