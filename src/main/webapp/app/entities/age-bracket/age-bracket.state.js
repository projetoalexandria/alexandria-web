(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('age-bracket', {
            parent: 'entity',
            url: '/age-bracket',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'alexandriaApp.ageBracket.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/age-bracket/age-brackets.html',
                    controller: 'AgeBracketController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ageBracket');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('age-bracket-detail', {
            parent: 'entity',
            url: '/age-bracket/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'alexandriaApp.ageBracket.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/age-bracket/age-bracket-detail.html',
                    controller: 'AgeBracketDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ageBracket');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AgeBracket', function($stateParams, AgeBracket) {
                    return AgeBracket.get({id : $stateParams.id});
                }]
            }
        })
        .state('age-bracket.new', {
            parent: 'age-bracket',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/age-bracket/age-bracket-dialog.html',
                    controller: 'AgeBracketDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                minAge: null,
                                maxAge: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('age-bracket', null, { reload: true });
                }, function() {
                    $state.go('age-bracket');
                });
            }]
        })
        .state('age-bracket.edit', {
            parent: 'age-bracket',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/age-bracket/age-bracket-dialog.html',
                    controller: 'AgeBracketDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AgeBracket', function(AgeBracket) {
                            return AgeBracket.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('age-bracket', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('age-bracket.delete', {
            parent: 'age-bracket',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/age-bracket/age-bracket-delete-dialog.html',
                    controller: 'AgeBracketDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AgeBracket', function(AgeBracket) {
                            return AgeBracket.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('age-bracket', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
