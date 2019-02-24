jQuery(function ($) {
    var util = {
        postJson: function (url, data, success, error) {
            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                type: 'post',
                url: url,
                dataType: 'json',
                data: data,
                cache: false,
                success: success,
                error: error
            });
        }
    };
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
                if ($(this).find('a.nav-link').attr('href') === location.pathname) {
                    $(this).addClass('active open');
                    $(this).append('<span class="selected"></span>');
                    $parentMenu.addClass('open');
                    $subMenu.css('display', 'block');

                }
            });
        } else {
            if ($(this).find('a.nav-link').attr('href') === location.pathname) {
                $(this).addClass('active open');
                $(this).append('<span class="selected"></span>');
            }
        }
    });
    /**
     * End of select active tab in side bar
     */

    var $dtResponsive = $(".dt-responsive");
    if ($dtResponsive.length) {
        $dtResponsive.dataTable({
            language: {
                aria: {
                    sortAscending: ": activate to sort column ascending",
                    sortDescending: ": activate to sort column descending"
                },
                emptyTable: "Pas d'enregistremen disponible",
                infoEmpty: "Pas d'enregistremen disponible",
                infoFiltered: "(filtered1 from _MAX_ total entries)",
                lengthMenu: "_MENU_ entries",
                zeroRecords: "Pas d'enregistremen disponible"
            },
            responsive: {details: {type: "column", target: "tr"}},
            order: [1, "asc"],
            pageLength: 10,
            pagingType: "bootstrap_extended",
            searching: false,
            "bLengthChange": false,
            "bInfo": false,
        })
    }

    /**
     * Debut etablissements page
     */
    var $saveEtab = $('#save-etab');
    if ($saveEtab.length) {
        var data = {};
        $('#new-etab').on('click', function () {
            data = {
                id: '',
                nom: '',
                adresse: ''
            };
            $('#nom-etab').val('');
            $('#adresse-etab').val('');
        });
        $('.modifier-etab').on('click', function () {
            var $parent = $(this).parent().parent();
            data = {
                id: $parent.data('id'),
                nom: $parent.data('nom'),
                adresse: $parent.data('adresse')
            };
            console.log('im here');
            console.log( data);
            $('#nom-etab').val(data.nom);
            $('#adresse-etab').val(data.adresse);
        });
        $('.supprimer-etab').on('click', function () {
            var $parent = $(this).parent().parent();
            data = {
                id: $parent.data('id')
            };
            util.postJson('/etablissements/supprimer', JSON.stringify(data), function (response) {
                if (response.length && response[0] === 'ok') {
                    alert('supression effectue');
                    location.reload();
                }
            })
        });
        $saveEtab.on('click', function () {
            data.nom = $('#nom-etab').val() ;
            data.adresse = $('#adresse-etab').val() ;
            if(data.nom !== '' && data.adresse !=='') {
                util.postJson('/etablissements/enregistrer', JSON.stringify(data), function (response) {
                    if(response.length && response[0]==='ok'){
                        alert('Enregistrement effectue');
                        location.reload();
                    }
                })
            }else{
                alert('tout les champs sont obligatoirs');
            }
        })

    }
    /**
     * Fin etablissements page
     */

    /**
     * Debut salle page
     */
    var $saveSalle = $('#save-salle');
    if ($saveSalle.length) {
        var data = {};
        $('#new-salle').on('click', function () {
            data = {
                id: '',
                nom: '',
                capacite: '',
                nbrpc:'',
                etablissement: ''
            };
            $('#nom-salle').val('');
            $('#capacite-salle').val('');
            $('#nbrpc-salle').val('');
            $('#etab-salle').val(-1);
        });
        $('.modifier-salle').on('click', function () {
            var $parent = $(this).parent().parent();
            data = {
                id: $parent.data('id'),
                nom: $parent.data('nom'),
                capacite: $parent.data('capacite'),
                nbrPc:$parent.data('nbrpc'),
                etablissement: $parent.data('etablissement')
            };

            console.log('im here');
            console.log( data);
            $('#nom-salle').val(data.nom);
            $('#capacite-salle').val(data.capacite);
            $('#nbrpc-salle').val(data.capacite);
            $('#etab-salle').val(data.etablissement);
        });
        $('.supprimer-salle').on('click', function () {
            var $parent = $(this).parent().parent();
            data = {
                id: $parent.data('id')
            };
            util.postJson('/salles/supprimer', JSON.stringify(data), function (response) {
                if (response.length && response[0] === 'ok') {
                    alert('supression effectue');
                    location.reload();
                }
            })
        });
        $saveSalle.on('click', function () {
            data.nom = $('#nom-salle').val();
            data.capacite= $('#capacite-salle').val();
            data.nbrPc= $('#nbrpc-salle').val();
            data.etablissement= $('#etab-salle').val();

            if(data.nom !== '' && data.capacite !==''&& data.nbrPc !==''&& data.etablissement !=-1) {
                util.postJson('/salles/enregistrer', JSON.stringify(data), function (response) {
                    if(response.length && response[0]==='ok'){
                        alert('Enregistrement effectue');
                        location.reload();
                    }
                })
            }else{
                alert('tout les champs sont obligatoirs');
            }
        })

    }
    /**
     * Fin salle page
     */
});