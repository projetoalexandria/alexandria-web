(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('AgeBracketDetailController', AgeBracketDetailController);

    AgeBracketDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'AgeBracket'];

    function AgeBracketDetailController($scope, $rootScope, $stateParams, entity, AgeBracket) {
        var vm = this;
        vm.ageBracket = entity;
        vm.load = function (id) {
            AgeBracket.get({id: id}, function(result) {
                vm.ageBracket = result;
            });
        };
        var unsubscribe = $rootScope.$on('alexandriaApp:ageBracketUpdate', function(event, result) {
            vm.ageBracket = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
