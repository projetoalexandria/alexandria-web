(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('AcquisitionTypeDeleteController',AcquisitionTypeDeleteController);

    AcquisitionTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'AcquisitionType'];

    function AcquisitionTypeDeleteController($uibModalInstance, entity, AcquisitionType) {
        var vm = this;
        vm.acquisitionType = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            AcquisitionType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
