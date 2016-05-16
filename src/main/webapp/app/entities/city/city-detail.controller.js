(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('CityDetailController', CityDetailController);

    CityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'City', 'State'];

    function CityDetailController($scope, $rootScope, $stateParams, entity, City, State) {
        var vm = this;
        vm.city = entity;
        vm.load = function (id) {
            City.get({id: id}, function(result) {
                vm.city = result;
            });
        };
        var unsubscribe = $rootScope.$on('alexandriaApp:cityUpdate', function(event, result) {
            vm.city = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
