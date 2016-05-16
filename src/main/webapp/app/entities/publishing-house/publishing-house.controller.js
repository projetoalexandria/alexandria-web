(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('PublishingHouseController', PublishingHouseController);

    PublishingHouseController.$inject = ['$scope', '$state', 'PublishingHouse'];

    function PublishingHouseController ($scope, $state, PublishingHouse) {
        var vm = this;
        vm.publishingHouses = [];
        vm.loadAll = function() {
            PublishingHouse.query(function(result) {
                vm.publishingHouses = result;
            });
        };

        vm.loadAll();
        
    }
})();
