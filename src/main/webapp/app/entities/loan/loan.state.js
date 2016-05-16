(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('loan', {
            parent: 'entity',
            url: '/loan',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'alexandriaApp.loan.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/loan/loans.html',
                    controller: 'LoanController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('loan');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('loan-detail', {
            parent: 'entity',
            url: '/loan/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'alexandriaApp.loan.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/loan/loan-detail.html',
                    controller: 'LoanDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('loan');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Loan', function($stateParams, Loan) {
                    return Loan.get({id : $stateParams.id});
                }]
            }
        })
        .state('loan.new', {
            parent: 'loan',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/loan/loan-dialog.html',
                    controller: 'LoanDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                loanDate: null,
                                devolutionDate: null,
                                maxDevolutionDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('loan', null, { reload: true });
                }, function() {
                    $state.go('loan');
                });
            }]
        })
        .state('loan.edit', {
            parent: 'loan',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/loan/loan-dialog.html',
                    controller: 'LoanDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Loan', function(Loan) {
                            return Loan.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('loan', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('loan.delete', {
            parent: 'loan',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/loan/loan-delete-dialog.html',
                    controller: 'LoanDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Loan', function(Loan) {
                            return Loan.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('loan', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
