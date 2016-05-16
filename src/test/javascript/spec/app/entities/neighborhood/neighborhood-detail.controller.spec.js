'use strict';

describe('Controller Tests', function() {

    describe('Neighborhood Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockNeighborhood, MockCity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockNeighborhood = jasmine.createSpy('MockNeighborhood');
            MockCity = jasmine.createSpy('MockCity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Neighborhood': MockNeighborhood,
                'City': MockCity
            };
            createController = function() {
                $injector.get('$controller')("NeighborhoodDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'alexandriaApp:neighborhoodUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
