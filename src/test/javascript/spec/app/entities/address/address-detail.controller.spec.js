'use strict';

describe('Controller Tests', function() {

    describe('Address Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAddress, MockNeighborhood;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAddress = jasmine.createSpy('MockAddress');
            MockNeighborhood = jasmine.createSpy('MockNeighborhood');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Address': MockAddress,
                'Neighborhood': MockNeighborhood
            };
            createController = function() {
                $injector.get('$controller')("AddressDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'alexandriaApp:addressUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
