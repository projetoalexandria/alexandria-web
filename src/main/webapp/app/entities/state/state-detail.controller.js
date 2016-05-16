(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('StateDetailController', StateDetailController);

    StateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'State', 'Country'];

    function StateDetailController($scope, $rootScope, $stateParams, entity, State, Country) {
        var vm = this;
        vm.state = entity;
        vm.load = function (id) {
            State.get({id: id}, function(result) {
                vm.state = result;
            });
        };
        var unsubscribe = $rootScope.$on('alexandriaApp:stateUpdate', function(event, result) {
            vm.state = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
