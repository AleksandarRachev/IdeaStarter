window.addEventListener('DOMContentLoaded', function() {
    function getInfo() {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:9999/posts/all', true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status >= 200 && status <= 299) {
                    const data = JSON.parse(xhr.responseText);
                    for(var i = 0;i<data.length;i++){
                        const cid = "comment"+i;
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
                                                 <div class="container">
                                                    <div class="row justify-content-center">
                                                    </div>
                                                    <div class="row py-2 justify-content-center">
                                                        <div class="col-12 col-lg-6  col-md-8 " data-form-type="formoid">
                                                            <form action=${"/posts/donate/"+data[i].id} method="POST" class="mbr-form form-with-styler">
                                                                <div class="dragArea row">
                                                                    <div class="form-group col" data-for="donate">
                                                                        <input type="number" step="0.01" name="donate" placeholder="Amount" data-form-field="Donate" required="required" class="form-control display-7" id="donate">
                                                                    </div>
                                                                    <div class="col-auto input-group-btn"><button type="submit" class="btn btn-primary display-4">Donate</button></div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                 </div>
                                                 <hr>
                                                 <img class="img-fluid rounded" src="assets/images/background4.jpg">
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
                    const content = `<div class="media mb-4">
                                     <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
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