(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('LoanController', LoanController);

    LoanController.$inject = ['$scope', '$state', 'Loan'];

    function LoanController ($scope, $state, Loan) {
        var vm = this;
        vm.loans = [];
        vm.loadAll = function() {
            Loan.query(function(result) {
                vm.loans = result;
            });
        };

        vm.loadAll();
        
    }
})();
