(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('PublishingHouseDetailController', PublishingHouseDetailController);

    PublishingHouseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'PublishingHouse', 'Cep'];

    function PublishingHouseDetailController($scope, $rootScope, $stateParams, entity, PublishingHouse, Cep) {
        var vm = this;
        vm.publishingHouse = entity;
        vm.load = function (id) {
            PublishingHouse.get({id: id}, function(result) {
                vm.publishingHouse = result;
            });
        };
        var unsubscribe = $rootScope.$on('alexandriaApp:publishingHouseUpdate', function(event, result) {
            vm.publishingHouse = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
