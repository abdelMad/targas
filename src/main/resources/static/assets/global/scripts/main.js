jQuery(function ($) {
    /**
     * select active tab in side bar
     */
    console.log(location.pathname);
    var $navItem = $('.nav-item.start');
    $navItem.each(function () {
        var $parentMenu = $(this);
        var $subMenu = $(this).find('ul.sub-menu');
        if ($subMenu.length) {
            $subMenu.find('li.nav-item.start').each(function () {
                console.log('Im a sub menu');
                console.log($(this).find('a.nav-link').attr('href'));
                if ($(this).find('a.nav-link').attr('href') === location.pathname) {
                    $(this).addClass('active open');
                    $(this).append('<span class="selected"></span>');
                    $parentMenu.addClass('open');
                    $subMenu.css('display', 'block');
                    console.log('activated here');

                }
            });
        } else {
            console.log('not a sub menu');
            console.log($(this).find('a.nav-link').attr('href'));

            if ($(this).find('a.nav-link').attr('href') === location.pathname) {
                $(this).addClass('active open');
                $(this).append('<span class="selected"></span>');
                console.log('activated not submenu ===xxxxx here');

            }
        }
    });
    /**
     * End of select active tab in side bar
     */

});