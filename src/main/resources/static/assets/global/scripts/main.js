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
            console.log(data);
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
            data.nom = $('#nom-etab').val();
            data.adresse = $('#adresse-etab').val();
            if (data.nom !== '' && data.adresse !== '') {
                util.postJson('/etablissements/enregistrer', JSON.stringify(data), function (response) {
                    if (response.length && response[0] === 'ok') {
                        alert('Enregistrement effectue');
                        location.reload();
                    }
                })
            } else {
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
                nbrpc: '',
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
                nbrPc: $parent.data('nbrpc'),
                etablissement: $parent.data('etablissement')
            };

            console.log('im here');
            console.log(data);
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
            data.capacite = $('#capacite-salle').val();
            data.nbrPc = $('#nbrpc-salle').val();
            data.etablissement = $('#etab-salle').val();

            if (data.nom !== '' && data.capacite !== '' && data.nbrPc !== '' && data.etablissement != -1) {
                util.postJson('/salles/enregistrer', JSON.stringify(data), function (response) {
                    if (response.length && response[0] === 'ok') {
                        alert('Enregistrement effectue');
                        location.reload();
                    }
                })
            } else {
                alert('tout les champs sont obligatoirs');
            }
        })

    }
    /**
     * Fin salle page
     */
    /**
     * Debut cours page
     */
    var $saveCours = $('#save-cours');
    if ($saveCours.length) {
        var data = {};
        $('#new-cours').on('click', function () {
            data = {
                id: '',
                nom: '',
                mh: ''
            };
            $('#nom-cours').val('');
            $('#mh-cours').val('');
        });
        $('.modifier-cours').on('click', function () {
            var $parent = $(this).parent().parent();
            data = {
                id: $parent.data('id'),
                nom: $parent.data('nom'),
                mh: $parent.data('mh'),
            };

            console.log('im here');
            console.log(data);
            $('#nom-cours').val(data.nom);
            $('#mh-cours').val(data.mh);
        });
        $('.supprimer-cours').on('click', function () {
            var $parent = $(this).parent().parent();
            data = {
                id: $parent.data('id')
            };
            util.postJson('/cours/supprimer', JSON.stringify(data), function (response) {
                if (response.length && response[0] === 'ok') {
                    alert('supression effectue');
                    location.reload();
                }
            })
        });
        $saveCours.on('click', function () {
            data.nom = $('#nom-cours').val();
            data.mh = $('#mh-cours').val();

            if (data.nom !== '' && data.mh !== '') {
                util.postJson('/cours/enregistrer', JSON.stringify(data), function (response) {
                    if (response.length && response[0] === 'ok') {
                        alert('Enregistrement effectue');
                        location.reload();
                    }
                })
            } else {
                alert('tout les champs sont obligatoirs');
            }
        })

    }
    /**
     * Fin cours page
     */

    /**
     * Debut Filiere page
     */
    var $saveFiliere = $('#save-filiere');
    if ($saveFiliere.length) {
        var data = {};
        $('#new-filiere').on('click', function () {
            data = {
                id: '',
                nom: '',
                etabs: []
            };
            $('#nom-filiere').val('');
            $('#mh-cours').val('');
            $('.etabs-filiere').prop('checked', false);
        });
        $('.modifier-filiere').on('click', function () {
            var $parent = $(this).parent().parent();
            data = {
                id: $parent.data('id'),
                nom: $parent.data('nom'),
                etabs: []
            };
            var etabs = [];
            $parent.find('.etabs-list').each(function () {
                etabs.push($(this).data('etab'));
            });
            console.log(etabs);
            data.etabs = etabs;
            console.log('im here');
            console.log(data);
            $('#nom-filiere').val(data.nom);
            $('.etabs-filiere').prop('checked', false);
            for (var i = 0; i < data.etabs.length; i++) {
                $('#etab' + data.etabs[i]).prop('checked', true);
            }
        });
        $('.supprimer-filiere').on('click', function () {
            var $parent = $(this).parent().parent();
            data = {
                id: $parent.data('id')
            };
            util.postJson('/filieres/supprimer', JSON.stringify(data), function (response) {
                if (response.length && response[0] === 'ok') {
                    alert('supression effectue');
                    location.reload();
                }
            })
        });
        $saveFiliere.on('click', function () {
            data.nom = $('#nom-filiere').val();
            var etabs = [];
            console.log('hello')
            $('.etabs-filiere:checked').each(function () {
                etabs.push($(this).val());
            });
            data.etabs = etabs;
            console.log(data);
            if (data.nom !== '') {
                util.postJson('/filieres/enregistrer', JSON.stringify(data), function (response) {
                    if (response.length && response[0] === 'ok') {
                        alert('Enregistrement effectue');
                        location.reload();
                    }
                })
            } else {
                alert('tout les champs sont obligatoirs');
            }
        })

    }
    /**
     * Fin Filiere page
     */
    //============
    /**
     * Debut Groupe page
     */
    var $saveGroupe = $('#save-groupe');
    if ($saveGroupe.length) {
        var data = {};
        $('#new-groupe').on('click', function () {
            data = {
                id: '',
                nom: '',
                abbreviation: '',
                filiere: ''
            };
            $('#nom-groupe').val('');
            $('#abbreviation-groupe').val('');
            $('#filiere-groupe').val(-1);
        });
        $('.modifier-groupe').on('click', function () {
            var $parent = $(this).parent().parent();
            data = {
                id: $parent.data('id'),
                nom: $parent.data('nom'),
                abbreviation: $parent.data('abbreviation'),
                filiere: $parent.data('filiere')
            };

            $('#nom-groupe').val(data.nom);
            $('#abbreviation-groupe').val(data.abbreviation);
            $('#filiere-groupe').val(data.filiere);
        });
        $('.supprimer-groupe').on('click', function () {
            var $parent = $(this).parent().parent();
            data = {
                id: $parent.data('id')
            };
            util.postJson('/groupes/supprimer', JSON.stringify(data), function (response) {
                if (response.length && response[0] === 'ok') {
                    alert('supression effectue');
                    location.reload();
                }
            })
        });
        $saveGroupe.on('click', function () {
            data.nom = $('#nom-groupe').val();
            data.abbreviation = $('#abbreviation-groupe').val();
            data.filiere = $('#filiere-groupe').val();
            console.log(data);
            if (data.nom !== '' && data.filiere != -1) {
                util.postJson('/groupes/enregistrer', JSON.stringify(data), function (response) {
                    if (response.length && response[0] === 'ok') {
                        alert('Enregistrement effectue');
                        location.reload();
                    }
                })
            } else {
                alert('tout les champs sont obligatoirs');
            }
        });
        $ddlCoursGrpContainerClone = $('#ddl-cours-grp-affect-container').clone();
        var $ddlCoursGrpContainer = $('#ddl-cours-grp-affect-container');
        $ddlCoursGrpContainer.children().remove();

        $('.groupe-affect').on('click', function (e) {
            var $parent = $(this).parent().parent();
            data = {
                id: $parent.data('id')
            };
            $('#nom-groupe-affectation').val($parent.data('nom'));
            $('#abbreviation-groupe-affectation').val($parent.data('abbreviation'));
            util.postJson('/groupes/affectation/cours', JSON.stringify(data), function (data) {
                console.log(data);
                $ddlCoursGrpContainer.html($ddlCoursGrpContainerClone.html());
                $ddlCoursGrpContainer.find('#grp-cours-affect').select2({
                    dropdownAutoWidth: 'true',
                    width: '100%',
                    data: data,
                    placeholder: 'Selectionner les cours a affecter',
                    "language": {
                        "noResults": function () {
                            return "plus de cours a affecter a ce groupe";
                        }
                    }
                });

            });
        });

        $('#save-affect-grp').on('click', function (e) {
            data.cours = $('#grp-cours-affect').val();
            util.postJson('/groupes/affectation/save', JSON.stringify(data), function (response) {
                if (response.length && response[0] === 'ok') {
                    alert('Enregistrement effectue');
                    location.reload();
                }
            })
        });
        $('.supprimer-affect-groupe').on('click', function (e) {
            e.preventDefault();
            var data = JSON.stringify({
                    id: $(this).parent().parent().parent().data('id'),
                    cours: $(this).data('id')
                })
            ;
            if (confirm("Etes vous sur de vouloir supprimer ce cours pour ce groupe")) {
                util.postJson('/groupes/affectation/supprimer', data, function (response) {
                    if (response.length && response[0] === 'ok') {
                        alert('cours supprime avec succes');
                        location.reload();
                    }
                })
            }
        });

    }
    /**
     * Fin Groupe page
     */
    /**
     * Debut Niveau page
      */
    var $saveNiveau = $('#save-niveau');
    if ($saveNiveau.length) {
        var data = {};
        $('#new-niveau').on('click', function () {
            data = {
                id: '',
                nom: '',
            };
            $('#nom-niveau').val('');
        });
        $('.modifier-niveau').on('click', function () {
            var $parent = $(this).parent().parent();
            data = {
                id: $parent.data('id'),
                nom: $parent.data('nom'),
            };
            console.log('im here');
            console.log(data);
            $('#nom-niveau').val(data.nom);
        });
        $('.supprimer-niveau').on('click', function () {
            var $parent = $(this).parent().parent();
            data = {
                id: $parent.data('id')
            };
            util.postJson('/niveaux/supprimer', JSON.stringify(data), function (response) {
                if (response.length && response[0] === 'ok') {
                    alert('supression effectue');
                    location.reload();
                }
            })
        });
        $saveNiveau.on('click', function () {
            data.nom = $('#nom-niveau').val();
            if (data.nom !== '' ) {
                util.postJson('/niveaux/enregistrer', JSON.stringify(data), function (response) {
                    if (response.length && response[0] === 'ok') {
                        alert('Enregistrement effectue');
                        location.reload();
                    }
                })
            } else {
                alert('tout les champs sont obligatoirs');
            }
        })

    }
    /**
     * Fin Niveau page
      */
    /**
     * Affectation page
     */
    var $saveAffect = $('#save-affect');
    if ($saveAffect.length) {
        $('#cours-affect').select2('destroy');
        var $ddlCoursContainerClone = $('#ddl-cours-container').clone();
        var $ddlCoursContainer = $('#ddl-cours-container');
        var $ddlEnseignant = $('#enseignant-affect');
        $ddlCoursContainer.html('<p>Veuillez selectionner un enseignant</p>');
        $ddlEnseignant.select2({dropdownAutoWidth: 'true', width: '100%', placeholder: 'Selectionner un enseignant'});
        $ddlEnseignant.on('change', function () {
            util.postJson('/affectation/cours', JSON.stringify({id: $(this).val()}), function (data) {
                console.log(data);
                $ddlCoursContainer.html($ddlCoursContainerClone.html());
                $ddlCoursContainer.find('#cours-affect').select2({
                    dropdownAutoWidth: 'true',
                    width: '100%',
                    data: data,
                    placeholder: 'Selectionner les cours a affecter',
                    "language": {
                        "noResults": function () {
                            return "pas de cours a affecter a cet enseignant";
                        }
                    }
                });

            });
        });

        $('#ajouter-affect').on('click', function (e) {
            e.preventDefault();
            $('#anneescolaire').val($('#annee-scolaire-affect option:selected').html());

        });

        $saveAffect.on('click', function (e) {
            e.preventDefault();
            var data = JSON.stringify({
                enseignant: $ddlEnseignant.val(),
                cours: $('#cours-affect').val(),
                anneeScolaire: $('#annee-scolaire-affect').val()
            });
            console.log(data);
            util.postJson('/affectation/save', data, function (response) {
                if (response.length && response[0] === 'ok') {
                    alert('Enregistrement effectue');
                    location.reload();
                }
            });
        });
        $('.supprimer-affect').on('click', function (e) {
            e.preventDefault();
            var data = JSON.stringify({
                id: $(this).data('id')
            });
            if (confirm("Etes vous sur de vouloir supprimer l'affectation?")) {
                util.postJson('/affectation/supprimer', data, function (response) {
                    if (response.length && response[0] === 'ok') {
                        alert('Affectation supprime avec succes');
                        location.reload();
                    }
                })
            }
        });
    }
    /**
     * Fin Affectation page
     */
    /**
     * Debut Emploi page
     */
    var $saveEmploi = $('#save-emploi');
    if ($saveEmploi.length) {
        $.fn.datepicker.dates["fr"] = {
            days: ["dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi"],
            daysShort: ["dim.", "lun.", "mar.", "mer.", "jeu.", "ven.", "sam."],
            daysMin: ["d", "l", "ma", "me", "j", "v", "s"],
            months: ["janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"],
            monthsShort: ["janv.", "févr.", "mars", "avril", "mai", "juin", "juil.", "août", "sept.", "oct.", "nov.", "déc."],
            today: "Aujourd'hui",
            monthsTitle: "Mois",
            clear: "Effacer",
            weekStart: 1,
            format: "dd/mm/yyyy"
        };
        var date = new Date();
        date.setDate(date.getDate());
        $('.date-picker').datepicker('destroy');
        $('.date-picker').datepicker({
            startDate: date,
            language: "fr",
            todayHighlight: true,
            autoclose: true
        });
        $saveEmploi.on('click', function (e) {
            e.preventDefault();
            var data = JSON.stringify({
                nom: $('#nom-emploi').val(),
                dateDebut: $('#dd-emploi').val(),
                dateFin: $('#df-emploi').val()
            });
            console.log(data);
            util.postJson('/emploi/add', data, function (response) {
                if (response.length && response[0] === 'ok') {
                    alert('Enregistrement effectue');
                    location.reload();
                } else if (response.length && response[0] === 'exists') {
                    alert('Un emploi avec ces dates existes deja veuillez le modifier');
                }
            });
        });
    }

    //emploi
    var $calendar = $('#calendar');
    if ($calendar.length) {
        $calendar.fullCalendar('destroy');
        var momentDd = moment($calendar.data('dd'));
        var momentDf = moment($calendar.data('df'));
        console.log(momentDd.format("YYYY-MM-DD"))
        console.log(momentDf.format("YYYY-MM-DD"))
        $calendar.fullCalendar({
            defaultView: 'agendaWeek',
            selectable: true,
            dayClick: function (date, jsEvent, view) {
                console.log(date.format());

            },
            select: function (start, end, jsEvent, view) {
                console.log("from " + start.format() + " to " + end.format());
            }
        });
    }
    /**
     * Fin Emploi page
     */
});