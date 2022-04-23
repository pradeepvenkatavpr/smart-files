$('#loginBtn').click(function (e) {
    var form = $('.loginForm'),
        message = $('.login__msg');
    let data = {
        email: $('#email').val(),
        password: $('#password').val()
    }
    if(Object.values(data).filter(r => !r || r === '').length) {
        message.fadeIn().removeClass('alert-success').addClass('alert-danger');
        $('.login__text').html(`Please fill all the fields `);
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
        console.log('response ', response);
        $('.login__text').html(`${response.message}`);
        setTimeout(() => {
            window.location.href = `${response.result.redirectUrl}`;
        }, 1500);
        form.find('input:not([type="submit"]), textarea').val('');
    }

    // fail function
    function fail_func(data) {
        message.fadeIn().removeClass('alert-success').addClass('alert-danger');
        $('.login__text').html(`${data.responseJSON.message || 'Network Failure'} `);
    }
})
