(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gender', {
            parent: 'entity',
            url: '/gender',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'alexandriaApp.gender.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gender/genders.html',
                    controller: 'GenderController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gender');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('gender-detail', {
            parent: 'entity',
            url: '/gender/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'alexandriaApp.gender.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gender/gender-detail.html',
                    controller: 'GenderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gender');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Gender', function($stateParams, Gender) {
                    return Gender.get({id : $stateParams.id});
                }]
            }
        })
        .state('gender.new', {
            parent: 'gender',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gender/gender-dialog.html',
                    controller: 'GenderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gender', null, { reload: true });
                }, function() {
                    $state.go('gender');
                });
            }]
        })
        .state('gender.edit', {
            parent: 'gender',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gender/gender-dialog.html',
                    controller: 'GenderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gender', function(Gender) {
                            return Gender.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('gender', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gender.delete', {
            parent: 'gender',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gender/gender-delete-dialog.html',
                    controller: 'GenderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Gender', function(Gender) {
                            return Gender.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('gender', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
