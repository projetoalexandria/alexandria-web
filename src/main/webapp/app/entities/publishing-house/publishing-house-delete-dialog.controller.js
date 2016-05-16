(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('PublishingHouseDeleteController',PublishingHouseDeleteController);

    PublishingHouseDeleteController.$inject = ['$uibModalInstance', 'entity', 'PublishingHouse'];

    function PublishingHouseDeleteController($uibModalInstance, entity, PublishingHouse) {
        var vm = this;
        vm.publishingHouse = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            PublishingHouse.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
