$(function () {
    $(".ss-menu").ssMenu({
        theme: "theme-name",
        hideOnScroll: true,
        additionalCSS: ({
            'background' : 'red', //custom background color
            'color' : 'red', //custom text color
            'boxShadow' : '', //to add box shadow
            'textShadow' : '', //to add text shadow
        }),

    });
})