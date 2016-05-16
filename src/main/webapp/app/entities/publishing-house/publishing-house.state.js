(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('publishing-house', {
            parent: 'entity',
            url: '/publishing-house',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'alexandriaApp.publishingHouse.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/publishing-house/publishing-houses.html',
                    controller: 'PublishingHouseController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('publishingHouse');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('publishing-house-detail', {
            parent: 'entity',
            url: '/publishing-house/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'alexandriaApp.publishingHouse.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/publishing-house/publishing-house-detail.html',
                    controller: 'PublishingHouseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('publishingHouse');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PublishingHouse', function($stateParams, PublishingHouse) {
                    return PublishingHouse.get({id : $stateParams.id});
                }]
            }
        })
        .state('publishing-house.new', {
            parent: 'publishing-house',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publishing-house/publishing-house-dialog.html',
                    controller: 'PublishingHouseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                nationality: null,
                                cnpj: null,
                                site: null,
                                email: null,
                                additionalInformation: null,
                                active: false,
                                addressNumber: null,
                                phone: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('publishing-house', null, { reload: true });
                }, function() {
                    $state.go('publishing-house');
                });
            }]
        })
        .state('publishing-house.edit', {
            parent: 'publishing-house',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publishing-house/publishing-house-dialog.html',
                    controller: 'PublishingHouseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PublishingHouse', function(PublishingHouse) {
                            return PublishingHouse.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('publishing-house', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('publishing-house.delete', {
            parent: 'publishing-house',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publishing-house/publishing-house-delete-dialog.html',
                    controller: 'PublishingHouseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PublishingHouse', function(PublishingHouse) {
                            return PublishingHouse.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('publishing-house', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
