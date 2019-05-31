const urlParams = new URLSearchParams(window.location.search);
const postId = urlParams.get('postId');
const amount = urlParams.get('donate1');
window.addEventListener('DOMContentLoaded', function() {
    function getInfo() {
        const content = `
        <section class="engine"><a href="https://mobirise.info/p">site templates free download</a></section><section class="mbr-section form1 cid-rs2ZaGdhk1" id="form1-5">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="title col-12 col-lg-8">
                        <h2 class="mbr-section-title align-center pb-3 mbr-fonts-style display-2">
                            CREDIT CARD CONFIRMATION
                        </h2>
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="media-container-column col-lg-8" data-form-type="formoid">
                        <form action=${"/posts/donate/"+postId} method="POST" class="mbr-form form-with-styler" data-form-title="Mobirise Form"><input type="hidden" name="email" data-form-email="true" value="QN9v/ghVxw3iFlEnrWYq7RKNZVpaj59dmLE5WdYsrCSVwj82InoGjtI47UDDsNb57QwxjrllDw0bJ1zwdcC3csXDjY0QCC/OcixbSPoZ6UlVJN5WdZQC7w3rVinEmPYx">
                            <div class="row">
                                <div hidden="hidden" data-form-alert="" class="alert alert-success col-12">Thanks for filling out the form!</div>
                                <div hidden="hidden" data-form-alert-danger="" class="alert alert-danger col-12">
                                </div>
                            </div>
                            <div class="dragArea row">
                                <div class="col-md-4  form-group" data-for="name">
                                    <label for="name-form1-5" class="form-control-label mbr-fonts-style display-7">Credit card number</label>
                                    <input type="text" name="cardNumber" data-form-field="cardNumber" class="form-control display-7" id="cardNumber-form1-5">
                                </div>
                                <div class="col-md-4  form-group" data-for="email">
                                    <label for="email-form1-5" class="form-control-label mbr-fonts-style display-7">Code</label>
                                    <input type="text" name="code" data-form-field="Code" class="form-control display-7" id="code-form1-5">
                                </div>
                                <div class="dragArea row">
                                    <div class="form-group col" data-for="donate">
                                        <input value=${amount} type="hidden" name="donate">
                                    </div>
                                    <div class="col-auto input-group-btn"><button type="submit" class="btn btn-primary display-4">Donate</button></div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>`;
        document.getElementById('donateForm').innerHTML += content;
    }
    if(window.addEventListener) {
        window.addEventListener('load',getInfo,false);
    } else {
        window.attachEvent('onload',getInfo);
    }
});