(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cep', {
            parent: 'entity',
            url: '/cep',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'alexandriaApp.cep.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cep/ceps.html',
                    controller: 'CepController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cep');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cep-detail', {
            parent: 'entity',
            url: '/cep/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'alexandriaApp.cep.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cep/cep-detail.html',
                    controller: 'CepDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cep');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cep', function($stateParams, Cep) {
                    return Cep.get({id : $stateParams.id});
                }]
            }
        })
        .state('cep.new', {
            parent: 'cep',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cep/cep-dialog.html',
                    controller: 'CepDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                number: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cep', null, { reload: true });
                }, function() {
                    $state.go('cep');
                });
            }]
        })
        .state('cep.edit', {
            parent: 'cep',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cep/cep-dialog.html',
                    controller: 'CepDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cep', function(Cep) {
                            return Cep.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('cep', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cep.delete', {
            parent: 'cep',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cep/cep-delete-dialog.html',
                    controller: 'CepDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cep', function(Cep) {
                            return Cep.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('cep', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
