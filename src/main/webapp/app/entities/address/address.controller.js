(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('AddressController', AddressController);

    AddressController.$inject = ['$scope', '$state', 'Address'];

    function AddressController ($scope, $state, Address) {
        var vm = this;
        vm.addresses = [];
        vm.loadAll = function() {
            Address.query(function(result) {
                vm.addresses = result;
            });
        };

        vm.loadAll();
        
    }
})();
