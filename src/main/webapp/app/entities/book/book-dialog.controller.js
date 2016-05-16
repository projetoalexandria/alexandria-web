(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('BookDialogController', BookDialogController);

    BookDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Book', 'AgeBracket', 'AcquisitionType', 'PublishingHouse', 'Gender', 'Author'];

    function BookDialogController ($scope, $stateParams, $uibModalInstance, entity, Book, AgeBracket, AcquisitionType, PublishingHouse, Gender, Author) {
        var vm = this;
        vm.book = entity;
        vm.agebrackets = AgeBracket.query();
        vm.acquisitiontypes = AcquisitionType.query();
        vm.publishinghouses = PublishingHouse.query();
        vm.genders = Gender.query();
        vm.authors = Author.query();
        vm.load = function(id) {
            Book.get({id : id}, function(result) {
                vm.book = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('alexandriaApp:bookUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.book.id !== null) {
                Book.update(vm.book, onSaveSuccess, onSaveError);
            } else {
                Book.save(vm.book, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.publicationDate = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
