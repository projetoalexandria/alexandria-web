(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('AddressDetailController', AddressDetailController);

    AddressDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Address', 'Neighborhood'];

    function AddressDetailController($scope, $rootScope, $stateParams, entity, Address, Neighborhood) {
        var vm = this;
        vm.address = entity;
        vm.load = function (id) {
            Address.get({id: id}, function(result) {
                vm.address = result;
            });
        };
        var unsubscribe = $rootScope.$on('alexandriaApp:addressUpdate', function(event, result) {
            vm.address = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
