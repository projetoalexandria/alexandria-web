(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('StudentDialogController', StudentDialogController);

    StudentDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Student', 'Cep'];

    function StudentDialogController ($scope, $stateParams, $uibModalInstance, entity, Student, Cep) {
        var vm = this;
        vm.student = entity;
        vm.ceps = Cep.query();
        vm.load = function(id) {
            Student.get({id : id}, function(result) {
                vm.student = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('alexandriaApp:studentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.student.id !== null) {
                Student.update(vm.student, onSaveSuccess, onSaveError);
            } else {
                Student.save(vm.student, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
