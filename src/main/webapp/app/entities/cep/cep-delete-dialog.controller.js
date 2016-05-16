(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('CepDeleteController',CepDeleteController);

    CepDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cep'];

    function CepDeleteController($uibModalInstance, entity, Cep) {
        var vm = this;
        vm.cep = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Cep.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
