(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('neighborhood', {
            parent: 'entity',
            url: '/neighborhood',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'alexandriaApp.neighborhood.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/neighborhood/neighborhoods.html',
                    controller: 'NeighborhoodController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('neighborhood');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('neighborhood-detail', {
            parent: 'entity',
            url: '/neighborhood/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'alexandriaApp.neighborhood.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/neighborhood/neighborhood-detail.html',
                    controller: 'NeighborhoodDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('neighborhood');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Neighborhood', function($stateParams, Neighborhood) {
                    return Neighborhood.get({id : $stateParams.id});
                }]
            }
        })
        .state('neighborhood.new', {
            parent: 'neighborhood',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/neighborhood/neighborhood-dialog.html',
                    controller: 'NeighborhoodDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('neighborhood', null, { reload: true });
                }, function() {
                    $state.go('neighborhood');
                });
            }]
        })
        .state('neighborhood.edit', {
            parent: 'neighborhood',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/neighborhood/neighborhood-dialog.html',
                    controller: 'NeighborhoodDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Neighborhood', function(Neighborhood) {
                            return Neighborhood.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('neighborhood', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('neighborhood.delete', {
            parent: 'neighborhood',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/neighborhood/neighborhood-delete-dialog.html',
                    controller: 'NeighborhoodDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Neighborhood', function(Neighborhood) {
                            return Neighborhood.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('neighborhood', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
