(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('LoanDetailController', LoanDetailController);

    LoanDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Loan', 'Student', 'Book'];

    function LoanDetailController($scope, $rootScope, $stateParams, entity, Loan, Student, Book) {
        var vm = this;
        vm.loan = entity;
        vm.load = function (id) {
            Loan.get({id: id}, function(result) {
                vm.loan = result;
            });
        };
        var unsubscribe = $rootScope.$on('alexandriaApp:loanUpdate', function(event, result) {
            vm.loan = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
