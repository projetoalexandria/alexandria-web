'use strict';

describe('Controller Tests', function() {

    describe('City Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCity, MockState;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCity = jasmine.createSpy('MockCity');
            MockState = jasmine.createSpy('MockState');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'City': MockCity,
                'State': MockState
            };
            createController = function() {
                $injector.get('$controller')("CityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'alexandriaApp:cityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
