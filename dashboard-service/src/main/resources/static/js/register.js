$('#funRegister').click(function (e) {
    var form = $('.registerForm'),
        message = $('.register__msg');
    let data = {
        title: $('#title').val(),
        firstName: $('#firstName').val(),
        lastName: $('#lastName').val(),
        email: $('#email').val(),
        phone: $('#phone').val(),
        businessInterest: $('#businessInterest').val()
    }
    if(Object.values(data).filter(r => !r || r === '').length) {
        message.fadeIn().removeClass('alert-success').addClass('alert-danger');
        $('.register__text').html(`Please fill all the fields `);
        return;
    }
    $.ajax({
        type: 'POST',
        url: form.attr('action'),
        data: form.serialize()
    })
        .done(done_func)
        .fail(fail_func);
    // Success function
    function done_func(response) {
        message.fadeIn().removeClass('alert-danger').addClass('alert-success');
        $('.register__text').html(`${response.message}`);
        setTimeout(function () {
            message.fadeOut();
        }, 5000);
        form.find('input:not([type="submit"]), textarea').val('');
    }

    // fail function
    function fail_func(data) {
        message.fadeIn().removeClass('alert-success').addClass('alert-danger');
        $('.register__text').html(`${data.responseText || 'Network Failure'} `);
        setTimeout(function () {
            message.fadeOut();
        }, 10000);
    }
})
