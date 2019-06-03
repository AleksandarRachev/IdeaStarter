window.addEventListener('DOMContentLoaded', function() {
    function getInfo() {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:9999/posts/top', true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status >= 200 && status <= 299) {
                    const data = JSON.parse(xhr.responseText);
                    document.getElementById('container').innerHTML += `<h1><span style="display:inline-block; width: 30%;"></span>Top 5 ideas.</h1>`;
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
    if(window.addEventListener) {
        window.addEventListener('load',getInfo,false);
    } else {
        window.attachEvent('onload',getInfo);
    }
});

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

window.addEventListener('DOMContentLoaded', function() {
    function generalInfo() {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:9999/posts/info', true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status >= 200 && status <= 299) {
                    const data = JSON.parse(xhr.responseText);
                    const content = `<section class="engine"></section><section class="counters1 counters cid-rskhYSlLzO" id="counters1-6">
                                         <div class="container">
                                             <h2 class="mbr-section-title pb-3 align-center mbr-fonts-style display-2">
                                                 Statistics
                                             </h2>
                                             <div class="container pt-4 mt-2">
                                                 <div class="media-container-row">
                                                     <div class="card p-3 align-center col-12 col-md-6 col-lg-4">
                                                         <div class="panel-item p-3">
                                                             <div class="card-text">
                                                                 <h3 class="count pt-3 pb-3 mbr-fonts-style display-2">
                                                                       ${data.userCount}
                                                                 </h3>
                                                                 <h4 class="mbr-content-title mbr-bold mbr-fonts-style display-7">
                                                                     Users using the platform
                                                                 </h4>
                                                                 <p class="mbr-content-text mbr-fonts-style display-7">
                                                                     Feel free to join out community and share your ideas.
                                                                 </p>
                                                             </div>
                                                         </div>
                                                     </div>
                                                     <div class="card p-3 align-center col-12 col-md-6 col-lg-4">
                                                         <div class="panel-item p-3">
                                                             <div class="card-text">
                                                                 <h3 class="count pt-3 pb-3 mbr-fonts-style display-2">
                                                                       ${data.postsCount}
                                                                 </h3>
                                                                 <h4 class="mbr-content-title mbr-bold mbr-fonts-style display-7">
                                                                     Ideas shared
                                                                 </h4>
                                                                 <p class="mbr-content-text mbr-fonts-style display-7">
                                                                         You can watch trough other people's ideas and help them grow.
                                                                 </p>
                                                             </div>
                                                         </div>
                                                     </div>
                                                     <div class="card p-3 align-center col-12 col-md-6 col-lg-4">
                                                         <div class="panel-item p-3">
                                                             <div class="card-text">
                                                                 <h3 class="count pt-3 pb-3 mbr-fonts-style display-2">
                                                                       ${data.commentsWritten}
                                                                 </h3>
                                                                 <h4 class="mbr-content-title mbr-bold mbr-fonts-style display-7">
                                                                     Comments posted
                                                                 </h4>
                                                                 <p class="mbr-content-text mbr-fonts-style display-7">
                                                                        You can communicate with others and share your opinion.
                                                                 </p>
                                                             </div>
                                                         </div>
                                                     </div>
                                                 </div>
                                             </div>
                                        </div>
                                     </section>`;
                    document.getElementById('generalInfo').innerHTML += content;
                }
            }
        }
        xhr.send();
    }
    if(window.addEventListener) {
        window.addEventListener('load',generalInfo,false);
    } else {
        window.attachEvent('onload',generalInfo);
    }
});