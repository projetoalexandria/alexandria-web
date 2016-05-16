(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('AcquisitionTypeController', AcquisitionTypeController);

    AcquisitionTypeController.$inject = ['$scope', '$state', 'AcquisitionType'];

    function AcquisitionTypeController ($scope, $state, AcquisitionType) {
        var vm = this;
        vm.acquisitionTypes = [];
        vm.loadAll = function() {
            AcquisitionType.query(function(result) {
                vm.acquisitionTypes = result;
            });
        };

        vm.loadAll();
        
    }
})();
