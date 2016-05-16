'use strict';

describe('Controller Tests', function() {

    describe('AgeBracket Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAgeBracket;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAgeBracket = jasmine.createSpy('MockAgeBracket');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'AgeBracket': MockAgeBracket
            };
            createController = function() {
                $injector.get('$controller')("AgeBracketDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'alexandriaApp:ageBracketUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
