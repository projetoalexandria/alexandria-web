(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('LoanDeleteController',LoanDeleteController);

    LoanDeleteController.$inject = ['$uibModalInstance', 'entity', 'Loan'];

    function LoanDeleteController($uibModalInstance, entity, Loan) {
        var vm = this;
        vm.loan = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Loan.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
