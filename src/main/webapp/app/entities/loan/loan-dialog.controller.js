(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('LoanDialogController', LoanDialogController);

    LoanDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Loan', 'Student', 'Book'];

    function LoanDialogController ($scope, $stateParams, $uibModalInstance, entity, Loan, Student, Book) {
        var vm = this;
        vm.loan = entity;
        vm.students = Student.query();
        vm.books = Book.query();
        vm.load = function(id) {
            Loan.get({id : id}, function(result) {
                vm.loan = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('alexandriaApp:loanUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.loan.id !== null) {
                Loan.update(vm.loan, onSaveSuccess, onSaveError);
            } else {
                Loan.save(vm.loan, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.loanDate = false;
        vm.datePickerOpenStatus.devolutionDate = false;
        vm.datePickerOpenStatus.maxDevolutionDate = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
