(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('GenderDetailController', GenderDetailController);

    GenderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Gender'];

    function GenderDetailController($scope, $rootScope, $stateParams, entity, Gender) {
        var vm = this;
        vm.gender = entity;
        vm.load = function (id) {
            Gender.get({id: id}, function(result) {
                vm.gender = result;
            });
        };
        var unsubscribe = $rootScope.$on('alexandriaApp:genderUpdate', function(event, result) {
            vm.gender = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
