(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('AcquisitionTypeDetailController', AcquisitionTypeDetailController);

    AcquisitionTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'AcquisitionType'];

    function AcquisitionTypeDetailController($scope, $rootScope, $stateParams, entity, AcquisitionType) {
        var vm = this;
        vm.acquisitionType = entity;
        vm.load = function (id) {
            AcquisitionType.get({id: id}, function(result) {
                vm.acquisitionType = result;
            });
        };
        var unsubscribe = $rootScope.$on('alexandriaApp:acquisitionTypeUpdate', function(event, result) {
            vm.acquisitionType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
