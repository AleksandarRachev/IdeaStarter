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
                        </div>
                    </section>`;
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