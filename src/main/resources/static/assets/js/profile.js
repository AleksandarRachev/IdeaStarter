function checkLogged() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:9999/users/profile', true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status >= 200 && status <= 299) {
                const data = JSON.parse(xhr.responseText);
                if(data.id == null){
                    alert("You are not logged in.")
                    xhr.abort();
                }
                else{
                    location.replace("profile.html")
                }
            }
        }
    };
    xhr.send();
}

window.addEventListener('DOMContentLoaded', function() {
    function getUser() {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:9999/users/profile', true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status >= 200 && status <= 299) {
                    const data = JSON.parse(xhr.responseText);
                    var imageSrc;
                    if(data.imageUrl == null){
                        imageSrc  = "/assets/images/icon-122x122.png";
                    }
                    else{
                        imageSrc  = "http://localhost:9999/images/users/"+data.id;
                    }
                    document.getElementById('user').innerHTML = `<section class="engine"></section><section class="accordion2 cid-rrH0c0gDML" id="accordion2-9">
                        <div class="container">
                            <div class="media-container-row pt-5">
                                <div class="accordion-content">
                                    <div class="mbr-figure" style="width: 200px;">
                                        <img width="100" src=${imageSrc} alt="Mobirise">
                                         <form method="POST" action="/images" enctype="multipart/form-data">
                                            <input type="file" name="image" placeholder="Change profile pic"/>
                                            <button type="submit" class="btn btn-sm btn-primary display-7">Upload</button>
                                         </form>
                                    </div>
                                </div>
                                <div id="bootstrap-accordion_7" class="panel-group accordionStyles accordion pt-5 mt-3" role="tablist" aria-multiselectable="true">
                                    <div class="card">
                                        <div class="card-header" role="tab" id="headingOne">
                                            <h4 class="mbr-fonts-style display-5">
                                                <span class="sign mbr-iconfont inactive"></span>Name: ${data.firstName + " " + data.lastName}
                                            </h4>
                                            <h4 class="mbr-fonts-style display-5">
                                                <span class="sign mbr-iconfont inactive"></span>Email: ${data.email}
                                            </h4>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <form method="POST" action="/users/logout" align="right">
                              <button type="submit" class="btn btn-sm btn-primary display-7">Logout</button>
                           </form>
                        </div>
                    </section>
                    <div id="userPosts"></div>`;
                    getPostForUser(data.id);
                }
            }
        }
        xhr.send();
    }
    if(window.addEventListener) {
        window.addEventListener('load',getUser,false);
    } else {
        window.attachEvent('onload',getUser);
    }
});

function deletePost(postId){
    var delPost = confirm("Are you sure you want to delete that post?");
    if(delPost === false){
        return;
    }
    var url = "http://localhost:9999/posts/"+postId;
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", url, true);
    xhr.onload = function () {
    	var users = JSON.parse(xhr.responseText);
    	if (xhr.readyState == 4 && xhr.status == "200") {
    		console.table(users);
    	} else {
    		console.error(users);
    	}
    }
    xhr.send(null);
    location.reload();
}

function getPostForUser(userId) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:9999/posts/'+userId, true);
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
                                             <h1 class="mt-4">${data[i].title}
                                             <button class="btn btn-secondary btn-form display-4" onclick="deletePost(${data[i].id});">Delete</button></h1>
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
                                                        <form action=${"/posts/donate/"+data[i].id} method="POST" class="mbr-form form-with-styler">
                                                            <div class="dragArea row">
                                                                <div class="form-group col" data-for="donate">
                                                                    <input type="number" min="0" step="0.01" name="donate" placeholder="Amount" data-form-field="Donate" required="required" class="form-control display-7" id="donate">
                                                                </div>
                                                                <div class="col-auto input-group-btn"><button type="submit" class="btn btn-primary display-4">Donate</button></div>
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
                    document.getElementById('userPosts').innerHTML += content;
                    getComments(cid,data[i].id);
                }
            }
        }
    }
    xhr.send();
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
                                     </div>
                                 </div>`;
                    document.getElementById(cid).innerHTML += content;
                }
            }
        }
    }
    xhr.send();
}