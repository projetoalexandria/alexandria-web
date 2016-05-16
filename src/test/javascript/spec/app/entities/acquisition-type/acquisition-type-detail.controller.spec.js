'use strict';

describe('Controller Tests', function() {

    describe('AcquisitionType Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAcquisitionType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAcquisitionType = jasmine.createSpy('MockAcquisitionType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'AcquisitionType': MockAcquisitionType
            };
            createController = function() {
                $injector.get('$controller')("AcquisitionTypeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'alexandriaApp:acquisitionTypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
