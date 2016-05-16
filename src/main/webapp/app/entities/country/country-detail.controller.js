(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('CountryDetailController', CountryDetailController);

    CountryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Country'];

    function CountryDetailController($scope, $rootScope, $stateParams, entity, Country) {
        var vm = this;
        vm.country = entity;
        vm.load = function (id) {
            Country.get({id: id}, function(result) {
                vm.country = result;
            });
        };
        var unsubscribe = $rootScope.$on('alexandriaApp:countryUpdate', function(event, result) {
            vm.country = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
