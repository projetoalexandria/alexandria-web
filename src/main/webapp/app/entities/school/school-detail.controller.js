(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('SchoolDetailController', SchoolDetailController);

    SchoolDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'School', 'Cep', 'User'];

    function SchoolDetailController($scope, $rootScope, $stateParams, entity, School, Cep, User) {
        var vm = this;
        vm.school = entity;
        vm.load = function (id) {
            School.get({id: id}, function(result) {
                vm.school = result;
            });
        };
        var unsubscribe = $rootScope.$on('alexandriaApp:schoolUpdate', function(event, result) {
            vm.school = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
