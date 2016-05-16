(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('acquisition-type', {
            parent: 'entity',
            url: '/acquisition-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'alexandriaApp.acquisitionType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/acquisition-type/acquisition-types.html',
                    controller: 'AcquisitionTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('acquisitionType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('acquisition-type-detail', {
            parent: 'entity',
            url: '/acquisition-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'alexandriaApp.acquisitionType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/acquisition-type/acquisition-type-detail.html',
                    controller: 'AcquisitionTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('acquisitionType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AcquisitionType', function($stateParams, AcquisitionType) {
                    return AcquisitionType.get({id : $stateParams.id});
                }]
            }
        })
        .state('acquisition-type.new', {
            parent: 'acquisition-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acquisition-type/acquisition-type-dialog.html',
                    controller: 'AcquisitionTypeDialogController',
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
                    $state.go('acquisition-type', null, { reload: true });
                }, function() {
                    $state.go('acquisition-type');
                });
            }]
        })
        .state('acquisition-type.edit', {
            parent: 'acquisition-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acquisition-type/acquisition-type-dialog.html',
                    controller: 'AcquisitionTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AcquisitionType', function(AcquisitionType) {
                            return AcquisitionType.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('acquisition-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('acquisition-type.delete', {
            parent: 'acquisition-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acquisition-type/acquisition-type-delete-dialog.html',
                    controller: 'AcquisitionTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AcquisitionType', function(AcquisitionType) {
                            return AcquisitionType.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('acquisition-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
