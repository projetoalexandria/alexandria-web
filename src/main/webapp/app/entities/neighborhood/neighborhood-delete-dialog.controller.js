(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('NeighborhoodDeleteController',NeighborhoodDeleteController);

    NeighborhoodDeleteController.$inject = ['$uibModalInstance', 'entity', 'Neighborhood'];

    function NeighborhoodDeleteController($uibModalInstance, entity, Neighborhood) {
        var vm = this;
        vm.neighborhood = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Neighborhood.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
